package com.tf.restocompras.service;

import com.tf.restocompras.error.NotFoundException;
import com.tf.restocompras.model.supplier.Supplier;
import com.tf.restocompras.model.item.ItemCreateRequestDto;
import com.tf.restocompras.model.item.ItemResponseDto;
import com.tf.restocompras.model.item.ItemUpdateRequestDto;
import com.tf.restocompras.repository.ItemRepository;
import com.tf.restocompras.repository.ProductRepository;
import com.tf.restocompras.repository.SupplierRepository;
import com.tf.restocompras.repository.UserRepository;
import com.tf.restocompras.service.mapper.ItemMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service

public class ItemService {

    private final ItemMapper itemMapper;
    private final ItemRepository itemRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final SupplierRepository supplierRepository;

    public ItemService(ItemMapper itemMapper,
                       ItemRepository itemRepository,
                       ProductRepository productRepository, UserRepository userRepository, SupplierRepository supplierRepository) {
        this.itemMapper = itemMapper;
        this.itemRepository = itemRepository;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
        this.supplierRepository = supplierRepository;
    }

    public List<ItemResponseDto> findAll() {
        return itemRepository.findAll().stream()
                .map(itemMapper::mapEntityToDto).collect(Collectors.toList());
    }

    public ItemResponseDto findById(Long id) {
        var item = itemRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Item with id: " + id + " not found"));
        return itemMapper.mapEntityToDto(item);
    }

    public ItemResponseDto save(ItemCreateRequestDto itemCreateRequestDto) {
        var item = itemMapper.mapDtoToEntity(itemCreateRequestDto);
        var product = productRepository.findById(itemCreateRequestDto.getProductId())
                .orElseThrow(() -> new NotFoundException("Product not found with id " + itemCreateRequestDto.getProductId()));
        item.setProduct(product);
        itemRepository.save(item);
        return itemMapper.mapEntityToDto(item);
    }

    public ItemResponseDto update(ItemUpdateRequestDto itemUpdateRequestDto) {
        var item = itemRepository.findById(itemUpdateRequestDto.getId())
                .orElseThrow(() -> new NotFoundException("Item with id: " + itemUpdateRequestDto.getId() + " not found"));

        var product = productRepository.findById(itemUpdateRequestDto.getProductId())
                .orElseThrow(() -> new NotFoundException("Product not found with id " + itemUpdateRequestDto.getProductId()));
        item.setProduct(product);
        item.setName(itemUpdateRequestDto.getName());
        item.setPrice(itemUpdateRequestDto.getPrice());
        item.setDescription(itemUpdateRequestDto.getDescription());
        item.setImage(itemUpdateRequestDto.getImage());

        itemRepository.save(item);
        return itemMapper.mapEntityToDto(item);
    }

    public void deleteById(Long id) {
        var item = itemRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Item with id: " + id + " not found"));
        itemRepository.delete(item);
    }

    public List<ItemResponseDto> findBySupplierId(Long id) {
        Supplier supplier = supplierRepository.findById(id).orElseThrow(() -> new NotFoundException("User with id: " + id + " not found"));
        return itemRepository.findBySupplier(supplier).stream()
                .map(itemMapper::mapEntityToDto).collect(Collectors.toList());
    }
}