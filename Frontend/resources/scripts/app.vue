<template>

    <div class="navigation">
    <nav class="spacer stack stack--row stack--justify-space-between">
        <ul class="u--list-style-none stack stack--row stack--justify-space-between stack6">
            <li><router-link v-if="loggedIn" :to="{ name: 'booking' }">Book Car</router-link></li>
            <li><router-link v-if="loggedIn" :to="{ name: 'map' }">Map</router-link></li>
            <li><router-link v-if="loggedIn" :to="{ name: 'overview' }">My bookings</router-link></li>
            <li><router-link v-if="loggedIn" :to="{ name: 'profile' }">My profile</router-link></li>
        </ul>
        <ul class="u--list-style-none stack stack--row stack--justify-space-between stack3">
            <li><router-link class="button button--secondary button_navigation" v-if="!loggedIn" :to="{ name: 'register' }">Register</router-link></li>
            <li><router-link class="button button--primary button_navigation" v-if="!loggedIn" :to="{ name: 'login' }">Login</router-link></li>
            <li><button @click="logout" class="button button--primary button_navigation" v-if="loggedIn">Logout</button></li>
        </ul>
    </nav>
    </div>
    <main>
        <Suspense>
        <router-view></router-view>
    </Suspense>
    </main>

</template>

<script setup>
import {ref, computed, Suspense} from "vue"
import store from "./store"
import router from "./router/router.js"

const loggedIn = computed(() => {
    if(localStorage.getItem('token')){
        store.state.auth.status.loggedIn = true
    }
    return store.state.auth.status.loggedIn
})
function logout(){
      store.dispatch('auth/logout');
      router.push('/login');
}
</script>

