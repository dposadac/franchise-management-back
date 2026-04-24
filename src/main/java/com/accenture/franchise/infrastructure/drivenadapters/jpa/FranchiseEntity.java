package com.accenture.franchise.infrastructure.drivenadapters.jpa;

import com.accenture.franchise.domain.model.FranchiseStatus;
import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "franchises")
public class FranchiseEntity {

    @Id
    private UUID id;

    @Column(nullable = false)
    private String name;

    private String address;
    private String phone;

    @Column(nullable = false)
    private String email;

    @Enumerated(EnumType.STRING)
    private FranchiseStatus status;

    protected FranchiseEntity() {}

    public FranchiseEntity(UUID id, String name, String address, String phone, String email, FranchiseStatus status) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.email = email;
        this.status = status;
    }

    public UUID getId() { return id; }
    public String getName() { return name; }
    public String getAddress() { return address; }
    public String getPhone() { return phone; }
    public String getEmail() { return email; }
    public FranchiseStatus getStatus() { return status; }
}
