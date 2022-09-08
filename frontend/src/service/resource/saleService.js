import ApiService from '../api';

export default class SaleService extends ApiService{
    constructor(){
        super("/sales")
    }

    save(sales){
        return this.post(`/`, sales)
    }

    findSales(){
        return this.get(`/`)
    }

    sendSms(id){
        return this.get(`/${id}/notification`)
    }
}