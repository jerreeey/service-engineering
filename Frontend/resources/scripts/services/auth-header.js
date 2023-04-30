import store from '../store'
export default function authHeader() {
    let user =store.state.auth.user
  
    if (user && user.access_token) {
      return { Authorization: 'Bearer ' + user.access_token };
    } else {
      return {};
    }
  }