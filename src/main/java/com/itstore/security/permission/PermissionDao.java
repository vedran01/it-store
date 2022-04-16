package com.itstore.security.permission;

import com.itstore.security.identity.IdentityPermission;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;
import java.util.Set;

@Component
public class PermissionDao {

    private final EntityManager em;

    public PermissionDao(EntityManager em) {
        this.em = em;
    }

    @Cacheable(value = "permission-cache", key = "#user + '#' + #resources")
    @Transactional(readOnly = true)
    public List<IdentityPermission> getPermissions(String user, Set<String> resources) {

        String select = "SELECT  sid.id 'id', " +
                "res.key 'resource', " +
                "GROUP_CONCAT(distinct sc4.`key`, ':', sidp.permitted SEPARATOR '::') 'permissions', " +
                "GROUP_CONCAT(distinct sc5.`key`, ':', scg.permitted SEPARATOR '::') 'groupPermissions', " +
                "GROUP_CONCAT(distinct sc6.`key`, ':', spr.permitted SEPARATOR '::') 'rolePermissions' ";

        String from = "FROM sec_identity sid " +
                "LEFT JOIN sec_identity_group sidg ON sid.id = sidg.identity_id " +
                "LEFT JOIN sec_identity_role sidr ON sid.id = sidr.identity_id " +
                "LEFT JOIN sec_group  sg ON sg.id = sidg.group_id AND sg.enabled = true " +
                "LEFT JOIN sec_group_role sgr ON sgr.group_id = sg.id " +
                "LEFT JOIN sec_role sr ON (sr.id = sidr.role_id OR sr.id = sgr.role_id) AND sr.enabled = true " +
                "LEFT JOIN sec_permission_identity sidp ON sid.id = sidp.identity_id " +
                "LEFT JOIN sec_permission_group scg ON scg.group_id = sg.id " +
                "LEFT JOIN sec_permission_role spr ON spr.role_id = sr.id " +
                "LEFT JOIN sec_permission sc1 ON sc1.id = sidp.permission_id  " +
                "LEFT JOIN sec_permission sc2 ON sc2.id = scg.permission_id " +
                "LEFT JOIN sec_permission sc3 ON sc3.id = spr.permission_id " +
                "LEFT JOIN sec_resource res ON res.id = sc1.resource_id " +
                    "OR res.id = sc2.resource_id " +
                    "OR res.id = sc3.resource_id " +
                "LEFT JOIN sec_permission sc4 ON sc4.id = sc1.id AND sc4.resource_id = res.id " +
                "LEFT JOIN sec_permission sc5 ON sc5.id = sc2.id AND sc5.resource_id = res.id " +
                "LEFT JOIN sec_permission sc6 ON sc6.id = sc3.id AND sc6.resource_id = res.id ";


        String where = "WHERE sid.uuid = :userId ";

        if (resources != null) {
            where += "AND res.key IN (:resource) ";
        }

        String groupBy = "GROUP BY res.id";

        Query query = em.createNativeQuery(select + from + where + groupBy, IdentityPermission.class.getSimpleName());
        query.setParameter("userId", user);


        if (resources != null) {
            query.setParameter("resource", resources);
        }

        return  (List<IdentityPermission>) query.getResultList();

    }

}
