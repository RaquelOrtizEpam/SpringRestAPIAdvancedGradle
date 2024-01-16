package com.epam.esm.services.impl;

import com.epam.esm.dto.UserDto;
import com.epam.esm.model.GiftCertificate;
import com.epam.esm.model.Order;
import com.epam.esm.model.User;
import com.epam.esm.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class OrderService {
    @Autowired
    private OrderRepository orderRepository;

    @Override
    public OrderDto create(UserDto userDto) {
        User user = userRepository.create(userMapper.toEntity(userDto));

        return userMapper.toDto(user);
    }

}
