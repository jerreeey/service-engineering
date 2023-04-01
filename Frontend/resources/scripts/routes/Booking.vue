<template>
<div class="spacer">
    <div class="stack stack--justify-space-between stack5">
    <h1>Book car</h1>
    <div class="searchbar stack stack--row stack--justify-space-between">
        <div class=" stack stack--row stack--justify-space-between">
            <label class="stack" for="pickUpDate">Pick up date
                <input id="pickUpDate" type="date">
            </label>
            <label class="stack">Pick up time
                <input id="pickUpTime" type="time">
            </label>
            <label class="stack">Return date
                <input id="returnDate" type="date">
            </label>
            <label class="stack">Return time
                <input id="returnTime" type="time">
            </label>
        </div>
        <button class="button button--primary" type="text">Search</button>
    </div>
    </div>
    <div :key="i" v-for="i in Math.ceil(cars.length / 3)" class="booking_row stack stack--row stack5">
        <div class="booking_container stack stack3 stack--column" :key="`car_${index}`" v-for="(car,index) in  cars.slice((i-1)*3, i*3)">
            <h2 class="h3">{{ car.name }} - {{ car.model }}</h2>
            <div class="stack8 stack">
            <div class="stack">
                <div class="stack stack--row stack--justify-space-between">
                    <span>Year:</span>
                    <span>{{ car.year }}</span>
                </div>
                <div class="stack stack--row stack--justify-space-between">
                    <span>Price per day:</span>
                    <div>
                        <span></span>
                        <select name="currency">
                            <option>USD</option>
                        </select>
                    </div>
                </div>
            </div>
            <div class="booking_summary stack">
                <div class="stack stack--row stack--justify-space-between">
                    <span class="u--text-600">Date</span>
                    <span>28.03.2023 - 31.03.2023</span>
                </div>
                <div class="stack stack--row stack--justify-space-between">
                    <span class="u--text-600">Total price:</span>
                    <div>
                        <span>30 USD</span>
                    </div>
                </div>
            </div>
        </div>
            <button class="button button--primary" type="text">Book</button>
        </div>
    </div>
</div>
</template>

<script setup>
import {ref} from "vue"
const cars = ref([
    {
        name: "VW",
        model: "Golf",
        price: 20,
        year: "2010",

    },
    {
        name: "Ferrari",
        model: "Golf",
        price: 20,
        year: "2010",

    },
    {
        name: "Toyota",
        model: "Golf",
        price: 20,
        year: "2010",

    },
    {
        name: "Toyota",
        model: "Golf",
        price: 20,
        year: "2010",

    },
    {
        name: "Toyota",
        model: "Golf",
        price: 20,
        year: "2010",

    },
    {
        name: "Toyota",
        model: "Golf",
        price: 20,
        year: "2010",

    }
])
</script>
