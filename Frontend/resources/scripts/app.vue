<template>
    <div class="navigation">
    <nav class="spacer stack stack--row stack--justify-space-between">
        <ul class="u--list-style-none stack stack--row stack--justify-space-between stack6">
            <li><router-link :to="{ name: 'home' }">Home</router-link></li>
            <li><router-link :to="{ name: 'booking' }">Book Car</router-link></li>
            <li><router-link :to="{ name: 'map' }">Map</router-link></li>
            <li><router-link :to="{ name: 'overview' }">My bookings</router-link></li>
            <li><router-link :to="{ name: 'profile' }">My profile</router-link></li>
        </ul>
        <div>
            <a v-if="loggedIn" href="">Logout</a>
        </div>
    </nav>
    </div>
    <main>
        <router-view></router-view>
    </main>
</template>

<script setup>
import {ref} from "vue"
const loggedIn = ref(false)
</script>

