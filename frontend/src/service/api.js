import axios from 'axios';

export const baseURL = process.env.REACT_APP_APP_BACKEND_URL ?? "http://localhost:8080"

const api = axios.create({
  baseURL: baseURL,
});

class ApiService {

  constructor(apiurl) {
    this.apiurl = apiurl;
  }

  async post(url, obj) {
    const requestUrl = `${this.apiurl}${url}`
    return api.post(requestUrl, obj)
  }

  async put(url, obj) {
    const requestUrl = `${this.apiurl}${url}`
    return api.put(requestUrl, obj);
  }

  async delete(url) {
    const requestUrl = `${this.apiurl}${url}`
    return api.delete(requestUrl)
  }

  async get(url) {
    try {
      const response = api.get(`${this.apiurl}${url}`)
      return response
    } catch (error) {
      return false;
    }
  }
}

export default ApiService;