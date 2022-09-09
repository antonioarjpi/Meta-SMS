import React from "react";
import { toast } from 'react-toastify';
import icon from "../../assets/user.png";
import './styles.css'
import SaleService from './../../service/resource/saleService';

function handleClick(id) {
    const service = new SaleService ();
    service.sendSms(id)
    .then(() => {
      toast.success("SMS enviado! ")
    })
}

function NotificationButton(props) {
  return (
    <button className="dsmeta-red-btn" disabled={props.disabled} onClick={() => handleClick(props.saleId)}>
      <img src={icon} alt="Notificar" />
    </button>
  );
}

export default NotificationButton;
