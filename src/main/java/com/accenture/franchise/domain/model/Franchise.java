package com.accenture.franchise.domain.model;

import java.util.UUID;

public class Franchise {

    private UUID id;
    private String name;
    private String address;
    private String phone;
    private String email;
    private FranchiseStatus status;

    public Franchise(UUID id, String name, String address, String phone, String email, FranchiseStatus status) {
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

    public void activate() { this.status = FranchiseStatus.ACTIVE; }
    public void deactivate() { this.status = FranchiseStatus.INACTIVE; }
}
