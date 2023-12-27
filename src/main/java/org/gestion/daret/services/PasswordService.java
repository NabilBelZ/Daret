package org.gestion.daret.services;

import lombok.AllArgsConstructor;
import lombok.Data;

public interface PasswordService {
    public String hashPassword(String plainPassword);

    public boolean verifyPassword(String plainPassword, String hashedPassword);
}
