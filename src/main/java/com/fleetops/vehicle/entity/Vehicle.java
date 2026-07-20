package com.fleetops.vehicle.entity;

import java.time.LocalDateTime;

import com.fleetops.fleet.entity.Fleet;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "vehicles")
@Getter
@Setter
@NoArgsConstructor
public class Vehicle {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  
  @Column(nullable = false,unique = true)
  private String registrationNumber;
  
  @Column(nullable = false)
  private String manufacturer;
  
  @Column(nullable = false)
  private String model;
  
  @Column(nullable = false)
  private Integer capacity;
  
  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private VehicleStatus status;
  
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "fleet_id",nullable= false)
  private Fleet fleet;
  
  @Column(nullable = false,updatable = false)
  private LocalDateTime createdAt;
  
  @PrePersist
  public void prePersist() {
	createdAt = LocalDateTime.now();
	
	if(status == null) {
		status = VehicleStatus.AVAILABLE;
	}
  }
  
}
