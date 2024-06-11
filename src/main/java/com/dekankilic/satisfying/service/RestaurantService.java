package com.dekankilic.satisfying.service;

import com.dekankilic.satisfying.dto.CreateRestaurantRequest;
import com.dekankilic.satisfying.dto.RestaurantDto;
import com.dekankilic.satisfying.exception.ResourceNotFoundException;
import com.dekankilic.satisfying.mapper.RestaurantMapper;
import com.dekankilic.satisfying.model.Address;
import com.dekankilic.satisfying.model.Restaurant;
import com.dekankilic.satisfying.model.User;
import com.dekankilic.satisfying.repository.RestaurantRepository;
import com.dekankilic.satisfying.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RestaurantService {

    private final RestaurantRepository restaurantRepository;
    private final AddressService addressService;
    // private final UserService userService;
    private final UserRepository userRepository;

    public Restaurant createRestaurant(CreateRestaurantRequest createRestaurantRequest, User user){

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
        return restaurantRepository.save(restaurant);
    }

    public Restaurant updateRestaurant(Long restaurantId, CreateRestaurantRequest request){
        Restaurant restaurant = findRestaurantById(restaurantId);
        Restaurant willBeUpdated = RestaurantMapper.mapToRestaurant(request);
        willBeUpdated.setId(restaurantId);
        restaurantRepository.save(willBeUpdated);
        return willBeUpdated;
    }

    public boolean deleteRestaurant(Long restaurantId){
        Restaurant restaurant = findRestaurantById(restaurantId);
        restaurantRepository.deleteById(restaurant.getId());
        return true;
    }

    public List<Restaurant> getAllRestaurants(){ // only an admin can get all restaurants' information
        return restaurantRepository.findAll();
    }

    public List<Restaurant> searchRestaurant(String keyword){
        return restaurantRepository.findBySearchQuery(keyword);
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

    public Restaurant updateRestaurantStatus(Long restaurantId) {
        Restaurant restaurant = findRestaurantById(restaurantId);
        restaurant.setOpen(!restaurant.isOpen());
        return restaurantRepository.save(restaurant);
    }

    // 4:09:30
}
