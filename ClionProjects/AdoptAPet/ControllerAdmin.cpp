//
// Created by alex on 22.03.2016.
//

#include "ControllerAdmin.h"

bool ControllerAdmin::add(int age, std::string breed, std::string name, std::string photo) {
    Dog dog{age, breed, name, photo};
    return this->repository.add(dog);
}

bool ControllerAdmin::remove(int age, std::string breed, std::string name) {
    std::string str = "";
    Dog dog{age, breed, name, str};
    return this->repository.remove(dog);
}

int ControllerAdmin::update(int age, std::string breed, std::string name, int new_age, std::string new_breed,
                            std::string new_name, std::string new_photo) {
    std::string str = "";
    Dog dog{age, breed, name, str};
    Dog new_dog{new_age, new_breed, new_name, new_photo};
    return this->repository.update(dog, new_dog);
}

Vector<Dog> ControllerAdmin::get_all() {
    return this->repository.get_dogs();
}






