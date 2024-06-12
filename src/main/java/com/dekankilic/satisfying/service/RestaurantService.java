package com.dekankilic.satisfying.service;

import com.dekankilic.satisfying.dto.CreateRestaurantRequest;
import com.dekankilic.satisfying.dto.RestaurantDto;
import com.dekankilic.satisfying.dto.RestaurantResponseDto;
import com.dekankilic.satisfying.exception.ResourceNotFoundException;
import com.dekankilic.satisfying.mapper.RestaurantMapper;
import com.dekankilic.satisfying.mapper.RestaurantOutboxConverter;
import com.dekankilic.satisfying.model.Address;
import com.dekankilic.satisfying.model.Restaurant;
import com.dekankilic.satisfying.model.User;
import com.dekankilic.satisfying.repository.RestaurantRepository;
import com.dekankilic.satisfying.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RestaurantService {

    private final RestaurantRepository restaurantRepository;
    private final AddressService addressService;
    // private final UserService userService;
    private final UserRepository userRepository;
    private final RestaurantOutboxService restaurantOutboxService;

    public RestaurantResponseDto createRestaurant(CreateRestaurantRequest createRestaurantRequest, User user){

        // First, save the address gathered from createRestaurantRequest
        Address address = addressService.saveAddress(createRestaurantRequest.address());

        Restaurant restaurant = Restaurant.builder()
                .name(createRestaurantRequest.name())
                .description(createRestaurantRequest.description())
                .kitchenType(createRestaurantRequest.kitchenType())
                .openingHours(createRestaurantRequest.openingHours())
                .images(createRestaurantRequest.images())
                .contactInformation(createRestaurantRequest.contactInformation())
                .registrationDate(LocalDateTime.now())
                .owner(user)
                .address(address)
                .build();

        // Then, save the restaurant constructed from createRestaurantRequest
        Restaurant savedRestaurant = restaurantRepository.save(restaurant);

        // Then, save the outbox into the outbox table
        restaurantOutboxService.saveOutbox(RestaurantOutboxConverter.convertToOutbox(savedRestaurant));

        // Finally, map the Restaurant entity to RestaurantResponseDto and return it.
        return RestaurantMapper.mapToRestaurantResponseDto(savedRestaurant);
    }

    public RestaurantResponseDto updateRestaurant(Long restaurantId, CreateRestaurantRequest request){
        Restaurant restaurant = findRestaurantById(restaurantId);
        Restaurant willBeUpdated = RestaurantMapper.mapToRestaurant(request);
        willBeUpdated.setId(restaurantId);
        Restaurant savedRestaurant = restaurantRepository.save(willBeUpdated);
        return RestaurantMapper.mapToRestaurantResponseDto(savedRestaurant);
    }

    public boolean deleteRestaurant(Long restaurantId){
        Restaurant restaurant = findRestaurantById(restaurantId);
        restaurantRepository.deleteById(restaurant.getId());
        return true;
    }

    public List<RestaurantResponseDto> getAllRestaurants(){ // only an admin can get all restaurants' information
        List<Restaurant> restaurants = restaurantRepository.findAll();
        return restaurants.stream().map(RestaurantMapper::mapToRestaurantResponseDto).collect(Collectors.toList());
    }

    public List<RestaurantResponseDto> searchRestaurant(String keyword){
        List<Restaurant> restaurants =  restaurantRepository.findBySearchQuery(keyword);
        return restaurants.stream().map(RestaurantMapper::mapToRestaurantResponseDto).collect(Collectors.toList());
    }

    public Restaurant findRestaurantById(Long restaurantId){
        return restaurantRepository.findById(restaurantId).orElseThrow(() -> new ResourceNotFoundException("Restaurant", "restaurantId", restaurantId.toString()));
    }

    public Restaurant getRestaurantByUserId(Long userId){
        return restaurantRepository.findByOwnerId(userId).orElseThrow(() -> new ResourceNotFoundException("Restaurant", "userId", userId.toString()));
    }

    public RestaurantDto addToFavorites(Long restaurantId, User user){
        Restaurant restaurant = findRestaurantById(restaurantId);

        RestaurantDto restaurantDto = new RestaurantDto();
        restaurantDto.setId(restaurantId);
        restaurantDto.setDescription(restaurant.getDescription());
        restaurantDto.setTitle(restaurant.getName());
        restaurantDto.setImages(restaurant.getImages());

        boolean isFavorite = false;
        List<RestaurantDto> favorites = user.getFavorites();
        for(RestaurantDto favorite : favorites){
            if(favorite.getId().equals(restaurantId)){
                isFavorite = true;
                break;
            }
        }

        if(isFavorite){
            favorites.removeIf(favorite -> favorite.getId().equals(restaurantId));
        } else{
            favorites.add(restaurantDto);
        }

        userRepository.save(user);
        return restaurantDto;
    }

    public RestaurantResponseDto updateRestaurantStatus(Long restaurantId) {
        Restaurant restaurant = findRestaurantById(restaurantId);
        restaurant.setOpen(!restaurant.isOpen());
        Restaurant savedRestaurant = restaurantRepository.save(restaurant);
        return RestaurantMapper.mapToRestaurantResponseDto(savedRestaurant);
    }

    // 4:09:30
}
