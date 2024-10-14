package com.nxhu.eventosDeAutos.service;

import com.nxhu.eventosDeAutos.model.AddressEntity;

import java.util.List;

public interface IAddressService {
    public List<AddressEntity> getAddresses();

    public AddressEntity getAddress(Long address_id);

    public AddressEntity createAddress(AddressEntity address);

    public void deleteAddress(Long address_id);

    public AddressEntity updateAddress(Long address_id, String newStreet, String newCity, String newCountry);
}
