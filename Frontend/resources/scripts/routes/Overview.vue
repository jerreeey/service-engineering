<template>
<div class="spacer">
    <div class="stack stack--justify-space-between stack5">
    <h1>My bookings</h1>
    <div class="container overviewbar stack stack--row" :key="`booking_${index}`" v-for="(booking,index) in  bookings">
            <div>
                <h2 class="stack h6">Car</h2>
                <span>{{booking.make}} - {{ booking.model }}</span>
            </div>
                <div>                
                    <h2 class="stack h6">Pick up date</h2>
                    <span>{{booking.pickupDate}} {{ booking.pickupHour }}</span>
                </div>
                <div>
                    <h2 class="stack h6">Return date</h2>
                    <span>{{booking.returnDate}} {{ booking.returnHour}}</span>
                </div>

            <div class="returned">
            <button v-if="!booking.returned" class="button button--primary returned" v-bind:value="booking.bookingId" @click="returnCar(booking.bookingId)" type="submit">Return</button>
            <span  v-else>Already returned</span>
            </div>
        </div>
        

    </div>
</div>
</template>

<script setup>
import {ref} from "vue"
import BookingService from "../services/booking.service"
import store from '../store'

let userid = store.state.auth.user.userDTO.userId;

let bookings = ref(await BookingService.getBookings(userid).then(response => {
    return response
}))


async function returnCar(bookingid){
    await BookingService.returnCar(bookingid).then(response => {
        getBookings(userid)})
}
async function getBookings(userid){
    bookings.value = await BookingService.getBookings(userid).then(response => {
    return response
})
}

</script>