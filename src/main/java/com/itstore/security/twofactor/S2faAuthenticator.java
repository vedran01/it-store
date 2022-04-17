package com.itstore.security.twofactor;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.qrcode.QRCodeWriter;
import com.warrenstrange.googleauth.GoogleAuthenticator;
import org.apache.http.client.utils.URIBuilder;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Base64;

@Component
public class S2faAuthenticator {

    private static final String AUTH_2FA_LABEL = "IT-STORE";
    private static final String AUTH_2FA_ISSUER = "IT-STORE";
    QRCodeWriter writer = new QRCodeWriter();
    GoogleAuthenticator authenticator = new GoogleAuthenticator();

    public String generatePassword() {
        return authenticator.createCredentials().getKey();
    }

    public int code(String secret) {
        return authenticator.getTotpPassword(secret);
    }

    public boolean validate(String secret, int code){
        return authenticator.authorize(secret, code);
    }

    public String generateQRCode(String username, String secret) throws URISyntaxException, WriterException, IOException {

        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {

            BufferedImage image = generateCode(username, secret);

            ImageIO.write(image, "PNG", outputStream);

            return Base64.getEncoder().encodeToString(outputStream.toByteArray());
        }
    }
    private BufferedImage generateCode(String username, String secret) throws WriterException, URISyntaxException {

        var uri = generateUrl(username, secret).toString();

        var matrix = writer.encode(uri, BarcodeFormat.QR_CODE, 400, 400);

        return MatrixToImageWriter.toBufferedImage(matrix);
    }
    private URI generateUrl(String username, String secret) throws URISyntaxException {

        final URIBuilder uriBuilder = new URIBuilder();

        return uriBuilder.setScheme("otpauth")
                .setHost("totp")
                .setPath("/" + AUTH_2FA_LABEL + ":" + username)
                .setParameter("secret", secret)
                .setParameter("issuer", AUTH_2FA_ISSUER )
                .build();

    }

}
