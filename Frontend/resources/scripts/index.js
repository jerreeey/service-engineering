import { createApp } from 'vue'
import app from './app.vue'
import router from './router/router'
import store from './store'
import VeeValidate from 'vee-validate';
//import { createPinia } from 'pinia'

//const pinia = createPinia()
const application = createApp(app)
//application.use(pinia)
application.use(store)
application.use(VeeValidate)
application.use(router)
application.mount('#app')
