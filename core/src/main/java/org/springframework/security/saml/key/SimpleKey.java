/*
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 */

package org.springframework.security.saml.key;

public class SimpleKey {

    private String alias;
    private String privateKey;
    private String certificate;
    private String passphrase;
    private KeyType type;

    public SimpleKey() {
    }

    public SimpleKey(String alias,
                     String privateKey,
                     String certificate,
                     String passphrase,
                     KeyType type) {
        this.alias = alias;
        this.privateKey = privateKey;
        this.certificate = certificate;
        this.passphrase = passphrase;
        this.type = type;
    }

    public String getPrivateKey() {
        return privateKey;
    }

    public String getCertificate() {
        return certificate;
    }

    public String getPassphrase() {
        return passphrase;
    }

    public String getAlias() {
        return alias;
    }

    public KeyType getType() {
        return type;
    }

    public SimpleKey setAlias(String alias) {
        this.alias = alias;
        return this;
    }

    public SimpleKey setPrivateKey(String privateKey) {
        this.privateKey = privateKey;
        return this;
    }

    public SimpleKey setCertificate(String certificate) {
        this.certificate = certificate;
        return this;
    }

    public SimpleKey setPassphrase(String passphrase) {
        this.passphrase = passphrase;
        return this;
    }

    public SimpleKey setType(KeyType type) {
        this.type = type;
        return this;
    }

    public SimpleKey clone(String alias, KeyType type) {
        return new SimpleKey(alias, getPrivateKey(), getCertificate(), getPassphrase(), type);
    }

}
