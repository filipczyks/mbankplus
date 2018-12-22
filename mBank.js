var barsLoaded = false;

document.addEventListener("click", function(event) {
  if (!barsLoaded) {
    appendInfoToTransactions();
    barsLoaded = true;
  }
});

function getTransactions() {
  let domTransactions = getDomTransactions();

  return Object.keys(domTransactions).map(key => {
    let element = domTransactions[key];
    return parseTransactionFromDomTransaction(element);
  });
}

function getDomTransactions() {
  return document.querySelectorAll(".content-list-row");
}

function parseTransactionFromDomTransaction(element) {
  let amount = parseFloatFromAmount(
    element.querySelector(".column.amount").textContent
  );
  
  return {
    date: element.querySelector(".column.date").textContent,
    amount: amount,
    absAmount: Math.abs(amount)
  };
}

function parseFloatFromAmount(amount) {
  let removeWhitespace = amount => {
    return amount.replace(/ /g, "");
  };

  let comaToDot = amount => {
    return amount.replace(/,/g, ".");
  };

  let removePLN = amount => {
    return amount.replace(/PLN/g, "");
  };

  return parseFloat(removePLN(comaToDot(removeWhitespace(amount))));
}

function appendInfoToTransactions() {
  let biggestTransactionAmount = getBiggestTransactionAmount(getTransactions());

  getDomTransactions().forEach(domTransaction => {
    let transaction = parseTransactionFromDomTransaction(domTransaction);

    let barContainer = document.createElement("div");
    barContainer.appendChild(
      buildDomBar(transaction, biggestTransactionAmount)
    );
    domTransaction.prepend(barContainer);
  });
}

function buildDomBar(transaction, biggestTransactionAmount) {
  let percent = transaction.absAmount / biggestTransactionAmount;
  let shortPercent = Math.round(percent * 100);
  let bar = document.createElement("div");
  bar.textContent = shortPercent + "%";

  let isExpense = transaction.absAmount != transaction.amount;
  let color = isExpense ? "red" : "green";
  bar.setAttribute(
    "style",
    "background-color: " + color + ";" +
    "width:" + shortPercent + "%;" +
    "height: 5px;"
  );

  return bar;
}

function getBiggestTransactionAmount(transactions) {
  return transactions.reduce((previous, current) => {
    return previous.absAmount > current.absAmount ? previous : current;
  }).absAmount;
}
