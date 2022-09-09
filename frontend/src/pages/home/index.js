import React, { useEffect, useState } from "react";
import Card from "../../components/Card";
import Header from "../../components/Header";
import pt_br from "date-fns/locale/pt-BR";
import "react-datepicker/dist/react-datepicker.css";
import "./styles.css";
import NotificationButton from './../../components/NotificationButton/index';
import DatePicker, { registerLocale } from "react-datepicker";
import SaleService from './../../service/resource/saleService';
import { Skeleton } from 'primereact/skeleton';
registerLocale("pt_br", pt_br);

function Home() {

  const [sales, setSales] = useState([]);
  const [loading, setLoading] = useState(true);
  const min = new Date(new Date().setDate(new Date().getDate() - 365));
  const max = new Date();
  const [minDate, setMinDate] = useState(min);
  const [maxDate, setMaxDate] = useState(max);

  const service = new SaleService();

  useEffect(() => {
    setLoading(false);
    const dmin = minDate.toISOString().slice(0, 10);
    const dmax = maxDate.toISOString().slice(0, 10);
    service.findSales(dmin, dmax)
      .then(response => {
        setSales(response.data.content)
        setTimeout(() => {
          setLoading(true);
        }, 2000)
      })
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [minDate, maxDate])

  const rows = (
        <tr>
          <td><Skeleton width="403px" borderRadius="16px"></Skeleton></td>
          <td><Skeleton width="126px" borderRadius="16px"></Skeleton></td>
          <td><Skeleton width="9rem" borderRadius="16px"></Skeleton></td>
          <td><Skeleton width="5rem" borderRadius="16px"></Skeleton></td>
          <td><Skeleton width="5rem" borderRadius="16px"></Skeleton></td>
          <td><Skeleton width="5rem" borderRadius="16px"></Skeleton></td>
          <td>
            <div className="dsmeta-red-btn-container">
              <div className="dsmeta-red-btn">
                <NotificationButton disabled={true} />
              </div>
            </div>
          </td>
        </tr>
      )
    
  return (
    <>
      <Header />
      <main>
        <section id="sales">
          <div className="container">
            <Card title="Vendas" >
              <div>
                <div className="dsmeta-form-control-container">
                  <DatePicker
                    locale="pt_br"
                    selected={minDate}
                    onChange={(date) => setMinDate(date)}
                    className="dsmeta-form-control"
                    dateFormat="dd/MM/yyyy"
                  />
                </div>
                <div className="dsmeta-form-control-container">
                  <DatePicker
                    locale="pt_br"
                    selected={maxDate}
                    onChange={(date) => setMaxDate(date)}
                    className="dsmeta-form-control"
                    dateFormat="dd/MM/yyyy"
                  />
                </div>
              </div>
              <div>
                <div className="table-responsive">
                  <table className="dsmeta-sales-table">
                    <thead>
                      <tr>
                        <th className="show992">ID</th>
                        <th className="show576">Data</th>
                        <th>Vendedor</th>
                        <th style={{textAlign:'center'}} className="show992">Visitas</th>
                        <th style={{textAlign:'center'}} className="show992">Vendas</th>
                        <th>Total</th>
                        <th>Notificar</th>
                      </tr>
                    </thead>
                    <tbody>
                      {loading ? sales.map(item => {
                        return (
                          <tr key={item.id}>
                            <td className="show992">{item.id}</td>
                            <td className="show576">{new Date(item.date).toLocaleDateString()}</td>
                            <td>{item.sellerName}</td>
                            <td style={{textAlign:'center'}} className="show992">{item.visited}</td>
                            <td style={{textAlign:'center'}} className="show992">{item.deals}</td>
                            <td>R$ {item.amount.toFixed(2)}</td>
                            <td>
                              <div className="dsmeta-red-btn-container">
                                <div className="dsmeta-red-btn">
                                  <NotificationButton saleId={item.id}/>
                                </div>
                              </div>
                            </td>
                          </tr>
                        )
                      }) : (
                        <>
                          {rows}
                          {rows}
                          {rows}
                        </>
                      )}
                    </tbody>
                  </table>
                </div>
              </div>
            </Card>
          </div>
        </section>
      </main>
    </>
  );
}

export default Home;
