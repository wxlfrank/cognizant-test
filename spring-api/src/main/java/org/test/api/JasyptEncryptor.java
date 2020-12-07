package org.test.api;

import java.security.Provider;
import java.security.Security;
import java.util.Arrays;

import org.jasypt.encryption.StringEncryptor;
import org.jasypt.encryption.pbe.PooledPBEStringEncryptor;
import org.jasypt.encryption.pbe.config.SimpleStringPBEConfig;
import org.jasypt.iv.RandomIvGenerator;
import org.jasypt.salt.RandomSaltGenerator;;

public class JasyptEncryptor {
    public static final void main(String[] args) {
        StringEncryptor encryptor = stringEncryptor();
        if (args.length > 0) {
            Arrays.stream(args).forEach(arg -> System.out.println(encryptor.encrypt(arg)));
        } else {
            System.out.println(encryptor.encrypt("test"));
        }
    }

    public static StringEncryptor stringEncryptor() {
        PooledPBEStringEncryptor encryptor = new PooledPBEStringEncryptor();
        SimpleStringPBEConfig config = new SimpleStringPBEConfig();
        config.setPassword("password");
        config.setAlgorithm("PBEWithMD5AndDES");
        config.setKeyObtentionIterations("1000");
        config.setPoolSize("1");
        Provider provider = Security.getProvider("SunJCE");
        if(provider == null){
            provider = Security.getProvider("IBMJCE");
        }
        if(provider != null)
            config.setProvider(provider);
            config.setSaltGenerator(new RandomSaltGenerator());
        config.setIvGenerator(new RandomIvGenerator());
        config.setStringOutputType("base64");
        encryptor.setConfig(config);
        return encryptor;
    }
}