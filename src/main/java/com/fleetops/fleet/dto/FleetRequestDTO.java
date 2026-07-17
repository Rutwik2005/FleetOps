package com.fleetops.fleet.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class FleetRequestDTO {
   @NotBlank(message = "Fleet name is required")
   private String fleetName;
   @NotBlank(message = "Company name is required")
   private String companyName;
   @NotBlank(message = "Head office is required")
   private String headOffice;
   @Email(message = "Invalid email format")
   @NotBlank(message = "Contact email is required")
   private String contactEmail;
   @NotBlank(message = "Contact phone is required")
   private String contactPhone;
}
