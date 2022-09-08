import React from "react";
import "./styles.css";

function Card(props) {
  return (
    <div className="dsmeta-card">
      <h2 className="dsmeta-sales-title">{props.title}</h2>
      {props.children}
    </div>
  );
}

export default Card;
