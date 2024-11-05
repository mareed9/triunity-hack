package com.triunity.hack.controller;

import com.triunity.hack.model.MobileDevice;
import com.triunity.hack.repository.MobileDeviceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("triunity/api/phones")
@RequiredArgsConstructor
public class MobileDeviceController{
    private final MobileDeviceRepository mobileDeviceRepository;

    @GetMapping
    public List<MobileDevice>getAllDevices(){
        return mobileDeviceRepository.findAll();
    }

    @PostMapping
    public ResponseEntity<String> createMobileDevice(@RequestBody MobileDevice mobileDevice) {
        mobileDeviceRepository.save(mobileDevice);
        return new ResponseEntity<>("Mobile device created successfully", HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateMobileDevice(@PathVariable Long id, @RequestBody MobileDevice updatedMobileDevice) {
        return mobileDeviceRepository.findById(id)
                .map(mobileDevice -> {
                    mobileDevice.setBrand(updatedMobileDevice.getBrand());
                    mobileDevice.setModel(updatedMobileDevice.getModel());
                    mobileDevice.setColor(updatedMobileDevice.getColor());
                    mobileDevice.setScreenSize(updatedMobileDevice.getScreenSize());
                    mobileDevice.setBatteryCapacity(updatedMobileDevice.getBatteryCapacity());
                    mobileDevice.setCameraResolution(updatedMobileDevice.getCameraResolution());
                    mobileDevice.setMemoryCapacity(updatedMobileDevice.getMemoryCapacity());
                    mobileDevice.setStorageCapacity(updatedMobileDevice.getStorageCapacity());
                    mobileDevice.setPrice(updatedMobileDevice.getPrice());
                    mobileDeviceRepository.save(mobileDevice);
                    return new ResponseEntity<>("Mobile device updated successfully", HttpStatus.OK);
                })
                .orElse(new ResponseEntity<>("Mobile device not found", HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteMobileDevice(@PathVariable Long id) {
        if (mobileDeviceRepository.existsById(id)) {
            mobileDeviceRepository.deleteById(id);
            return new ResponseEntity<>("Mobile device deleted successfully", HttpStatus.OK);
        }
        return new ResponseEntity<>("Mobile device not found", HttpStatus.NOT_FOUND);
    }
}
