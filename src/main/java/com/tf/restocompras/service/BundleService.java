package com.tf.restocompras.service;

import com.tf.restocompras.error.NotFoundException;
import com.tf.restocompras.model.bundle.BundleCreateRequestDto;
import com.tf.restocompras.model.bundle.BundleResponseDto;
import com.tf.restocompras.model.bundle.BundleUpdateRequestDto;
import com.tf.restocompras.model.unit.Unit;
import com.tf.restocompras.repository.BundleRepository;
import com.tf.restocompras.repository.ItemRepository;
import com.tf.restocompras.repository.SupplierRepository;
import com.tf.restocompras.service.mapper.BundleMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BundleService {

    private final BundleMapper bundleMapper;
    private final BundleRepository bundleRepository;
    private final ItemRepository itemRepository;
    private final SupplierRepository supplierRepository;

    public BundleService(BundleMapper bundleMapper,
                         BundleRepository bundleRepository,
                         ItemRepository itemRepository,
                         SupplierRepository supplierRepository) {
        this.bundleMapper = bundleMapper;
        this.bundleRepository = bundleRepository;
        this.itemRepository = itemRepository;
        this.supplierRepository = supplierRepository;
    }

    public List<BundleResponseDto> findAll() {
        return bundleRepository.findAll().stream()
                .map(bundleMapper::mapEntityToDto)
                .collect(Collectors.toList());
    }

    public BundleResponseDto findById(Long id) {
        var bundle = bundleRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Bundle with id: " + id + " not found"));
        return bundleMapper.mapEntityToDto(bundle);
    }

    public BundleResponseDto save(BundleCreateRequestDto bundleCreateRequestDto) {
        var bundle = bundleMapper.mapDtoToEntity(bundleCreateRequestDto);
        
        var item = itemRepository.findById(bundleCreateRequestDto.getItemId())
                .orElseThrow(() -> new NotFoundException("Item not found with id " + bundleCreateRequestDto.getItemId()));
        bundle.setItem(item);
        
        var supplier = supplierRepository.findById(bundleCreateRequestDto.getSupplierId())
                .orElseThrow(() -> new NotFoundException("Supplier not found with id " + bundleCreateRequestDto.getSupplierId()));
        bundle.setSupplier(supplier);
        
        bundle.setUnit(Unit.valueOf(bundleCreateRequestDto.getUnit()));
        
        bundleRepository.save(bundle);
        return bundleMapper.mapEntityToDto(bundle);
    }

    public BundleResponseDto update(BundleUpdateRequestDto bundleUpdateRequestDto) {
        var bundle = bundleRepository.findById(bundleUpdateRequestDto.getId())
                .orElseThrow(() -> new NotFoundException("Bundle with id: " + bundleUpdateRequestDto.getId() + " not found"));

        var item = itemRepository.findById(bundleUpdateRequestDto.getItemId())
                .orElseThrow(() -> new NotFoundException("Item not found with id " + bundleUpdateRequestDto.getItemId()));
        bundle.setItem(item);

        var supplier = supplierRepository.findById(bundleUpdateRequestDto.getSupplierId())
                .orElseThrow(() -> new NotFoundException("Supplier not found with id " + bundleUpdateRequestDto.getSupplierId()));
        bundle.setSupplier(supplier);

        bundle.setQuantity(bundleUpdateRequestDto.getQuantity());
        bundle.setPrice(bundleUpdateRequestDto.getPrice());
        bundle.setUnit(Unit.valueOf(bundleUpdateRequestDto.getUnit()));

        bundleRepository.save(bundle);
        return bundleMapper.mapEntityToDto(bundle);
    }

    public void deleteById(Long id) {
        var bundle = bundleRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Bundle with id: " + id + " not found"));
        bundleRepository.delete(bundle);
    }

    public List<BundleResponseDto> findByItemId(Long itemId) {
        var item = itemRepository.findById(itemId)
                .orElseThrow(() -> new NotFoundException("Item with id: " + itemId + " not found"));
        return bundleRepository.findByItem(item).stream()
                .map(bundleMapper::mapEntityToDto)
                .collect(Collectors.toList());
    }

    public List<BundleResponseDto> findBySupplierId(Long supplierId) {
        var supplier = supplierRepository.findById(supplierId)
                .orElseThrow(() -> new NotFoundException("Supplier with id: " + supplierId + " not found"));
        return bundleRepository.findBySupplier(supplier).stream()
                .map(bundleMapper::mapEntityToDto)
                .collect(Collectors.toList());
    }
}
