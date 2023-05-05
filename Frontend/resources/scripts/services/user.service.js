import axios from 'axios';
import store from '../store'
import router from '../router/router.js'
import authHeader from './auth-header';

const API_URL = 'https://app-carrental-230415231716.azurewebsites.net/users';
const API_URL2 = 'https://app-carrental-230415231716.azurewebsites.net/authentications';

class UserService {
  login(user) {
    return axios
      .post(API_URL2 + '/sessions', {
        email: user.email,
        password: user.password
      })
      .then(response => {
        if (response.data.access_token) {
          localStorage.setItem('user', JSON.stringify(response.data));
        }
        return response.data;
      });
  }


  logout() {
    localStorage.removeItem('user');
  }

  register(user) {
    return axios.post(API_URL2, {
      email: user.email,
      password: user.password
    });
  }

  delete(user) {
    return axios.delete(API_URL + '/' + user.userId,
    {
      headers: authHeader()
    })
    .then(response => {
      if (response.status  == 200) {
        store.dispatch('auth/logout');
        router.push('/login');
      }

      return response;
    });
  }

  changePassword(user, newPassword) {
    return axios.put(API_URL + '/' + user.userId, {
      email: user.email,
      password: newPassword
    },
    {
      headers: authHeader()
    })
    .then(response => {
      if (response.data.password  != user.password) {
        let newUser = {
          userDTO: response.data,
          access_token: JSON.parse(localStorage.getItem('user')).access_token
        }
        localStorage.setItem('user', JSON.stringify(newUser));
      }

      return response.data;
    });
  }

  changeEmail(user, newEmail) {
    return axios.put(API_URL + '/' + user.userId, {
      email: newEmail,
      password : user.password
    },
    {
      headers: authHeader()
    })
    .then(response => {
      if (response.data.email  != user.email) {
        let newUser = {
          userDTO: response.data,
          access_token: JSON.parse(localStorage.getItem('user')).access_token
        }
        localStorage.setItem('user', JSON.stringify(newUser));
      }

      return response.data;
    });
  }
}

export default new UserService();