import { createWebHashHistory, createRouter } from 'vue-router'
import Booking from '../routes/Booking.vue'
import MapComponent from '../routes/Map.vue'
import Overview from '../routes/Overview.vue'
import Profile from '../routes/Profile.vue'
import Login from '../routes/Login.vue'
import Register from '../routes/Register.vue'
import store from '../store'

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
    meta: {
      requiresGuest: true
    }
  },
  {
    path: '/register',
    name: 'register',
    component: Register,
    meta: {
      requiresGuest: true
    }
  }
]

const router = createRouter({
  history: createWebHashHistory(),
  routes,
})

router.beforeEach((to, from, next) => {
  if (to.matched.some((record) => record.meta.requiresAuth)) {
    if (store.state.auth.status.loggedIn) {
      next()
    } else{
      router.push({ path: '/login' })
    }
  } 

  if (to.matched.some((record) => record.meta.requiresGuest)) {
    if (store.state.auth.status.loggedIn) {
      router.push({ path: '/' })
    } else{
      next()
    }
  } 

}

)

export default router
