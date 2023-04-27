<template>
<div class="spacer">
    <div class="stack stack--justify-space-between stack5">
    <h1>My bookings</h1>
    <div class="container overviewbar stack stack--row" :key="`car_${index}`" v-for="(car,index) in  cars">
            <div>
                <h2 class="stack h6">Car</h2>
                <span>{{car.name}} - {{ car.model }}</span>
            </div>
            <div>
                <h2 class="stack h6">Year</h2>
                <span>{{car.year}}</span>
            </div>
                <div>                
                    <h2 class="stack h6">Pick up date</h2>
                    <span>{{car.pickUpDate}} {{ car.pickupHour }}</span>
                </div>
                <div>
                    <h2 class="stack h6">Return date</h2>
                    <span>{{car.returnDate}} {{ car.returnHour}}</span>
                </div>

            <div class="returned">
            <button v-if="car.returned" class="button button--primary returned" type="submit">Return</button>
            <span  v-else>Already returned</span>
            </div>
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
        returned: false,
        pickUpDate: "28.03.2023",
        returnDate: "31.03.2023",
        pickupHour: "08:00",
        returnHour: "10:00"
    },
    {
        name: "Ferrari",
        model: "Golf",
        price: 20,
        year: "2010",
        returned: true,
        pickUpDate: "28.03.2023",
        returnDate: "31.03.2023",
        pickupHour: "08:00",
        returnHour: "10:00"
    },
    {
        name: "Toyota",
        model: "Golf",
        price: 20,
        year: "2010",
        returned: false,
        pickUpDate: "28.03.2023",
        returnDate: "31.03.2023",
        pickupHour: "08:00",
        returnHour: "10:00"
    },
    {
        name: "Toyota",
        model: "Golf",
        price: 20,
        year: "2010",
        returned: true,
        pickUpDate: "28.03.2023",
        returnDate: "31.03.2023",
        pickupHour: "08:00",
        returnHour: "10:00"
    },
    {
        name: "Toyota",
        model: "Golf",
        price: 20,
        year: "2010",
        returned: false,
        pickUpDate: "28.03.2023",
        returnDate: "31.03.2023",
        pickupHour: "08:00",
        returnHour: "10:00"
    },
    {
        name: "Toyota",
        model: "Golf",
        price: 20,
        year: "2010",
        returned: true,
        pickUpDate: "28.03.2023",
        returnDate: "31.03.2023",
        pickupHour: "08:00",
        returnHour: "10:00"
    }
])

</script>