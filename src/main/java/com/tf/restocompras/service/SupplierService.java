package com.tf.restocompras.service;

import com.tf.restocompras.error.NotFoundException;
import com.tf.restocompras.model.supplier.Supplier;
import com.tf.restocompras.model.supplier.SupplierCreateRequestDto;
import com.tf.restocompras.model.supplier.SupplierResponseDto;
import com.tf.restocompras.model.supplier.SupplierUpdateRequestDto;
import com.tf.restocompras.model.user.User;
import com.tf.restocompras.repository.SupplierRepository;
import com.tf.restocompras.repository.UserRepository;
import com.tf.restocompras.service.mapper.SupplierMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SupplierService {
    private final SupplierRepository supplierRepository;
    private final SupplierMapper supplierMapper;
    private final UserRepository userRepository;

    public SupplierService(SupplierRepository supplierRepository, SupplierMapper supplierMapper, UserRepository userRepository) {
        this.supplierRepository = supplierRepository;
        this.supplierMapper = supplierMapper;
        this.userRepository = userRepository;
    }

    public List<SupplierResponseDto> getAllSuppliers() {
        return supplierMapper.mapEntitiesToDtos(supplierRepository.findAll());
    }

    public SupplierResponseDto getSupplierById(Long id) {
        return supplierMapper.mapEntityToDto(supplierRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Supplier not found with id " + id)));
    }

    public SupplierResponseDto createSupplier(SupplierCreateRequestDto supplierCreateRequestDto) {
        Supplier supplier = supplierMapper.mapCreateDtoToEntity(supplierCreateRequestDto);
        return supplierMapper.mapEntityToDto(supplierRepository.save(supplier));
    }

    public SupplierResponseDto updateSupplier(SupplierUpdateRequestDto supplierUpdateRequestDto) {
        Supplier existingSupplier = supplierRepository.findById(supplierUpdateRequestDto.id())
                .orElseThrow(() -> new NotFoundException("Supplier not found with id " + supplierUpdateRequestDto.id()));

        supplierMapper.mapUpdateDtoToEntity(supplierUpdateRequestDto, existingSupplier);
        return supplierMapper.mapEntityToDto(supplierRepository.save(existingSupplier));
    }

    public void deleteSupplier(Long id) {
        if (!supplierRepository.existsById(id)) {
            throw new NotFoundException("Supplier not found with id " + id);
        }
        supplierRepository.deleteById(id);
    }

    @Transactional
    public SupplierResponseDto addUserToSupplier(Long supplierId, Long userId) {
        Supplier supplier = supplierRepository.findById(supplierId)
                .orElseThrow(() -> new NotFoundException("Supplier not found with id " + supplierId));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User not found with id " + userId));

        supplier.getUsers().add(user);
        user.setSupplier(supplier);

        return supplierMapper.mapEntityToDto(supplierRepository.save(supplier));
    }
}
