import ApiService from '../api';

export default class SaleService extends ApiService{
    constructor(){
        super("/sales")
    }

    save(sales){
        return this.post(`/`, sales)
    }

    findSales(dmin, dmax){
        return this.get(`?minDate=${dmin}&maxDate=${dmax}&sort=date,asc`)
    }

    sendSms(id){
        return this.get(`/${id}/notification`)
    }
}