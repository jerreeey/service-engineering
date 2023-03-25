import { createApp } from 'vue'
import app from './app.vue'
import router from './router/router'
//import { createPinia } from 'pinia'

//const pinia = createPinia()
const application = createApp(app)
//application.use(pinia)
application.use(router)
application.mount('#app')
