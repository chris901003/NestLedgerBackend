package org.xxooooxx.nestledger.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.io.InputStream;


@Slf4j
@Configuration
public class FirebaseConfig {
    @PostConstruct
    public void init() {
        try {
            InputStream serviceAccount = getClass().getClassLoader().getResourceAsStream("FirebaseAccountKey.json");
            if (serviceAccount == null) { throw new RuntimeException("FirebaseAccountKey.json not found"); }
            FirebaseOptions options = FirebaseOptions.builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .build();

            FirebaseApp.initializeApp(options);
            System.out.println("Firebase App initialized successfully.");
        } catch (Exception e) {
            log.error("Firebase App initialization failed.\n Error: {}", e.getMessage());
        }
    }
}
