import React from "react";
import Card from "../../components/Card";
import Header from "../../components/Header";
import "./styles.css";

function Home() {
  return (
    <>
      <Header />
      <main>
        <section id="sales">
          <div className="container">
            <Card />
          </div>
        </section>
      </main>
    </>
  );
}

export default Home;
