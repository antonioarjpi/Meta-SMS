import React from "react";
import logo from '../../assets/logo.svg'
import './styles.css'

function Header() {
  return (
    <header>
      <div className="dsmeta-logo-container">
        <img src={logo} alt="DSMeta" />
        <h1>Meta</h1>
        <p>
          Desenvolvido por
          <a href="https://github.com/antonioarjpi"> Antônio Araújo</a>
        </p>
      </div>
    </header>
  );
}

export default Header;
