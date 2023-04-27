import { createWebHashHistory, createRouter } from 'vue-router'
import Booking from '../routes/Booking.vue'
import MapComponent from '../routes/Map.vue'
import Overview from '../routes/Overview.vue'
import Profile from '../routes/Profile.vue'
import Login from '../routes/Login.vue'
import Register from '../routes/Register.vue'

const routes = [
  {
    path: '/',
    name: 'booking',
    component: Booking,
    meta: {
      requiresAuth: true
    }
  },
  {
    path: '/map',
    name: 'map',
    component: MapComponent,
    meta: {
      requiresAuth: true
    }
  },
  {
    path: '/profile',
    name: 'profile',
    component: Profile,
    meta: {
      requiresAuth: true
    }
  },
  {
    path: '/overview',
    name: 'overview',
    component: Overview,
    meta: {
      requiresAuth: true
    }
  },
  {
    path: '/login',
    name: 'login',
    component: Login,
  },
  {
    path: '/register',
    name: 'register',
    component: Register
  }
]

const router = createRouter({
  history: createWebHashHistory(),
  routes,
})

router.afterEach(async () => {
  // store.dispatch('updateStateFromUrl')
})

export default router
