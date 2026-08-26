package com.github.rhafaelcosta.commerce.order.core.domain.model.commons;

import com.github.rhafaelcosta.commerce.order.core.domain.model.FieldValidations;

import java.util.Objects;

public record Address(
        String street,
        String complement,
        String neighborhood,
        String number,
        String city,
        String state,
        ZipCode zipCode
) {
    public Address {
        FieldValidations.requiresNonBlank(street);
        FieldValidations.requiresNonBlank(neighborhood);
        FieldValidations.requiresNonBlank(city);
        FieldValidations.requiresNonBlank(number);
        FieldValidations.requiresNonBlank(state);
        Objects.requireNonNull(zipCode);
    }

    /**
     * Factory para um novo builder vazio
     */
    public static Builder builder() {
        return new Builder();
    }

    /**
     * Cria um builder pré-preenchido com os valores desta instância (toBuilder)
     */
    public Builder toBuilder() {
        return new Builder()
                .street(this.street)
                .complement(this.complement)
                .neighborhood(this.neighborhood)
                .number(this.number)
                .city(this.city)
                .state(this.state)
                .zipCode(this.zipCode);
    }


    public static final class Builder {
        private String street;
        private String complement;
        private String neighborhood;
        private String number;
        private String city;
        private String state;
        private ZipCode zipCode;

        private Builder() {
        }

        public Builder street(String street) {
            this.street = street;
            return this;
        }

        public Builder complement(String complement) {
            this.complement = complement;
            return this;
        }

        public Builder neighborhood(String neighborhood) {
            this.neighborhood = neighborhood;
            return this;
        }

        public Builder number(String number) {
            this.number = number;
            return this;
        }

        public Builder city(String city) {
            this.city = city;
            return this;
        }

        public Builder state(String state) {
            this.state = state;
            return this;
        }

        public Builder zipCode(ZipCode zipCode) {
            this.zipCode = zipCode;
            return this;
        }

        public Address build() {
            return new Address(street, complement, neighborhood, number, city, state, zipCode);
        }

    }

}