package com.dekankilic.satisfying.service;

import com.dekankilic.satisfying.dto.request.CreateOrderRequest;
import com.dekankilic.satisfying.exception.OrderStatusNotValidException;
import com.dekankilic.satisfying.exception.ResourceNotFoundException;
import com.dekankilic.satisfying.model.*;
import com.dekankilic.satisfying.repository.OrderItemRepository;
import com.dekankilic.satisfying.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final AddressService addressService;
    private final RestaurantService restaurantService;
    private final CartService cartService;

    public Order createOrder(CreateOrderRequest request, User user){
        Address shippingAddress = request.deliveryAddress();
        Address savedAddress = addressService.saveAddress(shippingAddress);
        if(!user.getAddresses().contains(savedAddress)){
            user.getAddresses().add(savedAddress);
            // userRepository.save(user);
        }
        Restaurant restaurant = restaurantService.findRestaurantById(request.restaurantId());

        Order order = Order.builder()
                .orderStatus("PENDING")
                .createdAt(new Date())
                .deliveryAddress(savedAddress)
                .user(user)
                .restaurant(restaurant)
                .build();

        Cart cart = cartService.findCartByUserId(user.getId());
        List<OrderItem> orderItems = new ArrayList<>();
        for(CartItem cartItem : cart.getCartItems()){
            OrderItem orderItem = OrderItem.builder()
                    .quantity(cartItem.getQuantity())
                    .totalPrice(cartItem.getTotalPrice())
                    .ingredients(cartItem.getIngredients())
                    .food(cartItem.getFood())
                    .build();
            OrderItem saved = orderItemRepository.save(orderItem);
            orderItems.add(saved);
        }

        order.setItems(orderItems);
        order.setTotalAmount(cartService.calculateCartTotal(cart));

        Order savedOrder = orderRepository.save(order);
        restaurant.getOrders().add(savedOrder);
        return savedOrder;
    }

    public Order updateOrder(Long orderId, String orderStatus){
        Order order = findOrderById(orderId);
        if(orderStatus.equals("OUT_FOR_DELIVERY")
                || orderStatus.equals("DELIVERED")
                || orderStatus.equals("COMPLETED")
                || orderStatus.equals("PENDING")
        ) {
            order.setOrderStatus(orderStatus);
            return orderRepository.save(order);
        }
        throw new OrderStatusNotValidException("OrderStatus");
    }

    public void cancelOrder(Long orderId){
        Order order = findOrderById(orderId);
        orderRepository.deleteById(orderId);
    }

    private Order findOrderById(Long orderId){
        return orderRepository.findById(orderId).orElseThrow(() -> new ResourceNotFoundException("Order", "orderId", orderId.toString()));
    }

    public List<Order> getOrdersOfUser(Long userId){
        return orderRepository.findByUserId(userId);
    }

    public List<Order> getOrdersOfRestaurant(Long restaurantId, String orderStatus){
        List<Order> orders = orderRepository.findByRestaurantId(restaurantId);
        if(orderStatus != null){
            orders = orders.stream().filter(order -> order.getOrderStatus().equals(orderStatus)).collect(Collectors.toList());
        }
        return orders;
    }
}
