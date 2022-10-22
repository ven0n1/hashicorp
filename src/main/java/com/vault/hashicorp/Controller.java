package com.vault.hashicorp;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.vault.core.VaultTemplate;
import org.springframework.vault.support.VaultResponseSupport;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("")
@Slf4j
public class Controller {

    private final Repository repository;
    private final Environment environment;
    private final VaultTemplate vaultTemplate;
    private final DatamartProperties props;

//    @PostConstruct
//    void init() {
//        log.error(environment.getProperty("foo"));
//        vaultTemplate.list("secret/").forEach(s -> log.error(s));
//    }

    @GetMapping()
    public ResponseEntity<List<DaoClass>> selectAll() {
        vaultTemplate.list("secret/").forEach(s -> log.error(s));
        List<DaoClass> daoClasses = repository.selectAll();
        return ResponseEntity.ok(daoClasses);
    }

    @GetMapping("list")
    public ResponseEntity<List<String>> list() {
        vaultTemplate.list("secrets/secret/").forEach(s -> log.error(s));
        log.error(environment.getProperty("foo"));
        log.error(environment.getProperty("qwe"));
        log.error(vaultTemplate.opsForToken().toString());
        log.error(environment.getProperty("spring.datasource.password"));
        log.error(environment.getProperty("spring.datasource.username"));
        return ResponseEntity.ok(vaultTemplate.list("secrets/secret/my-secret"));
    }

    @GetMapping("datamart")
    public ResponseEntity<Void> datamart() {
        log.error("props.url: " + props.url);
        log.error("props.username: " + props.username);
        log.error("props.password: " + props.password);
        return ResponseEntity.ok().build();
    }

    @GetMapping("vault/datamart")
    public ResponseEntity<Void> vaultCore() {
        Credentials credentials = new Credentials("username", "password");
        vaultTemplate.write("kv/datamart/vault", credentials);
        VaultResponseSupport<Credentials> creds = vaultTemplate
                .read("kv/datamart/vault", Credentials.class);
        VaultResponseSupport<DatamartProperties> properties = vaultTemplate
                .read("kv/datamart/vault", DatamartProperties.class);
        props.username = properties.getData().getUsername();
        props.password = properties.getData().getPassword();
        log.error("------------------------------PROPS----------------------------------");
        log.error("props.url: " + props.url);
        log.error("props.username: " + props.username);
        log.error("props.password: " + props.password);
        log.error("------------------------------RESPONSE----------------------------------");
        log.error("vaultCore.username: " + creds.getData().getUsername());
        log.error("vaultCore.password: " + creds.getData().getPassword());
        return ResponseEntity.ok().build();
    }

    @GetMapping("insert")
    public ResponseEntity<Void> insert() {
        repository.insert();
        return ResponseEntity.ok().build();
    }

}
