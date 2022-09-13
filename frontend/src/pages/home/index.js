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
import { Dialog } from "primereact/dialog";
import { Button } from "primereact/button";
import { InputNumber } from 'primereact/inputnumber';
import { InputText } from 'primereact/inputtext';
import { toast } from 'react-toastify';
registerLocale("pt_br", pt_br);

const initialValues = {
  sellerName: "",
  visited: 0,
  deals: 0,
  amount: 0.00,
  date: "",
}

function Home() {

  const [values, setValues] = useState(initialValues);
  const [sales, setSales] = useState([]);
  const [showConfirmDialog, setShowConfirmDialog] = useState(false);
  const [loading, setLoading] = useState(true);
  const [minDate, setMinDate] = useState(new Date(new Date().setDate(new Date().getDate() - 365)));
  const [maxDate, setMaxDate] = useState(new Date());

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
  }, [minDate, maxDate]);

  const onChange = (e) => {
    const { name, value } = e.target;
    setValues({ ...values, [name]: value });
  };

  const openDialog = () => {
    setShowConfirmDialog(true)
  }

  const cancelDialog = () => {
    setShowConfirmDialog(false)
  }

  const handleSubmit = () => {
    service.save(values)
    .then(response => {
      toast.success("Venda cadastrada com sucesso!");
      setShowConfirmDialog(false);
      setValues(initialValues);
    }).catch(error => {
      toast.error(error.response.data.message)
    })
  }

  const footerMessage =
    <div className="footer-message">
      <Button label="Cadastrar" icon="pi pi-check" type="submit" onClick={handleSubmit} />
      <Button label="Cancelar" icon="pi pi-times" type="button" onClick={cancelDialog} className="p-button-secondary mt-2" />
    </div>;

  const rows = (
    <tr>
      <td><Skeleton width="403px"></Skeleton></td>
      <td><Skeleton width="126px"></Skeleton></td>
      <td><Skeleton width="9rem"></Skeleton></td>
      <td><Skeleton className="ml-2" width="3rem"></Skeleton></td>
      <td><Skeleton className="ml-2" width="3rem"></Skeleton></td>
      <td><Skeleton className="ml-1" width="5rem"></Skeleton></td>
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
                <div>
                  <Button icon="pi pi-plus" className="p-button-rounded p-button-secondary" onClick={openDialog}/>
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
                        <th style={{ textAlign: 'center' }} className="show992">Visitas</th>
                        <th style={{ textAlign: 'center' }} className="show992">Vendas</th>
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
                            <td style={{ textAlign: 'center' }} className="show992">{item.visited}</td>
                            <td style={{ textAlign: 'center' }} className="show992">{item.deals}</td>
                            <td>R$ {item.amount.toFixed(2)}</td>
                            <td>
                              <div className="dsmeta-red-btn-container">
                                <div className="dsmeta-red-btn">
                                  <NotificationButton saleId={item.id} />
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

      <Dialog header={"Cadastro de venda"}
        visible={showConfirmDialog}
        style={{ width: '40vw' }}
        footer={footerMessage}
        modal={true}
        onHide={() => setShowConfirmDialog(false)}>
        <div className="row" style={{ display: 'flex' }}>
          <form className="col-md-12 row" onSubmit={handleSubmit}>
            <div className="col-md-6 mb-3">
              <strong color="black">Data de venda:</strong>
              <InputText className="dsmeta-form-control" id="date" name="date" type="date" value={values.date} onChange={onChange} />
            </div>
            <div className="col-md-12 mb-3">
              <strong color="black">Nome vendedor:</strong>
              <InputText className="dsmeta-form-control" id="sellerName" name="sellerName" type="text" value={values.sellerName} onChange={onChange} />
            </div>
            <div className="col-md-4 mb-3">
              <strong color="black">Visitas:</strong>
              <InputText className="dsmeta-form-control" id="visited" name="visited" type="number" value={values.visited} onChange={onChange} />
            </div>
            <div className="col-md-4 mb-3">
              <strong color="black">Positivados:</strong>
              <InputText className="dsmeta-form-control" id="deals" name="deals" type="number" value={values.deals} onChange={onChange} />
            </div>
            <div className="col-md-4  col-sm-12">
              <strong color="black">Valor total: </strong>
              <InputNumber inputId="currency-pt-BR" name="amount" value={values.amount} onValueChange={onChange} mode="currency" currency="BRL" locale="pt-BR" />
            </div>
          </form>
        </div>
      </Dialog>
    </>
  );
}

export default Home;
