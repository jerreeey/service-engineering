import { createWebHashHistory, createRouter } from 'vue-router'
import Home from '../routes/Home.vue'
import Booking from '../routes/Booking.vue'
import MapComponent from '../routes/Map.vue'
import Overview from '../routes/Overview.vue'
import Profile from '../routes/Profile.vue'
//import store from '../store/store'

const routes = [
  {
    path: '/',
    name: 'home',
    component: Home
  },
  {
    path: '/booking',
    name: 'booking',
    component: Booking
  },
  {
    path: '/map',
    name: 'map',
    component: MapComponent
  },
  {
    path: '/profile',
    name: 'profile',
    component: Profile
  },
  {
    path: '/overview',
    name: 'overview',
    component: Overview
  },
]

const router = createRouter({
  history: createWebHashHistory(),
  routes,
})

router.afterEach(async () => {
  // store.dispatch('updateStateFromUrl')
})

export default router
