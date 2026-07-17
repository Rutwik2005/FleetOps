package com.fleetops.fleet.entity;

import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="fleets")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Fleet {
	 @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;

	    @Column(nullable = false)
	    private String fleetName;

	    @Column(nullable = false)
	    private String companyName;

	    @Column(nullable = false)
	    private String headOffice;

	    @Column(nullable = false, unique = true)
	    private String contactEmail;

	    @Column(nullable = false)
	    private String contactPhone;

	    @Enumerated(EnumType.STRING)
	    private FleetStatus status;

	    private LocalDateTime createdAt;

	    @PrePersist
	    public void prePersist() {
	        createdAt = LocalDateTime.now();

	        if (status == null) {
	            status = FleetStatus.ACTIVE;
	        }
	    }
}
