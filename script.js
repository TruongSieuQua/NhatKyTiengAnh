"use strict"
const GetFamily = (item) => {
    let url = "https://dictionary.cambridge.org/vi/dictionary/english-vietnamese/";
    const {id, word, family} = item.item;
    return Object.keys(family).map(key => {
        if (family[key].length > 0) {
            let aElement = family[key].map((fam, index) => {
                let itemId = id + word + fam;
                return <a href={url + fam} target="_blank" className="f-item" key={itemId}>{" " + fam + " "}</a>;
            })
            return (
                <>
                    <span>{"(" + key + ")"}</span>
                    {aElement}
                </>
            );
        }
    })
};

const GetSynonyms = (item) => {
    let url = "https://www.vocabulary.com/dictionary/";
    // const family = item.item.family;
    // const id = item.item.id;
    // const word = item.item.word;
    const { id, word, synonyms } = item.item;
   if(synonyms == undefined || synonyms.length == 0) return <></>; 
   let a_synonyms =  synonyms.map(synonym => {
        let itemId = id + word + synonym;
        return (
            <>
                <a href={url + synonym} target="_blank" className="s-item" key={itemId}>{" " + synonym + " "}</a>;
            </>
        );
    })
    return <div className="synonyms">Synonyms: {a_synonyms}</div>
}

const ReplaceJSX = (item) => {
    let arr = item.item.str.split(item.item.oldValue);
    let result = [arr[0]];
    for (let i = 1; i < arr.length; i++) {
        result.push(item.item.newValue);
        result.push(arr[i]);
    }
    return <>{result}</>;
}

const GetMeanings = (item) => {
    const {id, word, meanings} = item.item;
    return meanings.map((meaning, index) => {
        let examples = meaning.examples.map((example, eIndex) => {
            let itemId = id + word + eIndex;
            return (
                <div className="example" key={itemId}>< ReplaceJSX item={{
                    "str": example,
                    "oldValue": " " + word,
                    "newValue": <> <span className="answer">{word}</span></>
                }} />
                </div>
            );
        });
        return (
            <>
                <div className="meaning">
                    <div className="en">{meanings[index].en}</div>
                    <div className="vi">{meanings[index].vi}</div>
                    <div className="examples">{examples}</div>
                </div>
            </>
        )
    });
}

const RenderData = (jsonData) => {
    const { useState } = React;
    const [toggledId, setToggledId] = useState(null);
    console.log(jsonData.jsonData);
    const {data} = jsonData.jsonData.data;
    function handleClick(id) {
        setToggledId(id);
    }

    return (
        <div className="word-cards">
            {data.map(item => {
                const family = < GetFamily item={item} />;
                item.meanings.word = item.word;
                const meanings = < GetMeanings item={item} />;
                let itemId = item.id + item.word;
                let toggleClassCheck = toggledId === itemId ? '' : ' disabled';
                return (
                    <div className={`card${toggleClassCheck}`} key={itemId}>
                        <div className="word">{item.word}</div>
                        <div className="ipa">{item.ipa}</div>
                        <div className="family">{family}</div>
                        <div className="list-meanings">{meanings}</div>
                        < GetSynonyms item={item} />
                        <button className="btn-answer"
                            onClick={() => handleClick(itemId)}>Answer</button>.
                    </div>
                )
            })}
        </div>
    )
}

function shuffle(){
    data.data.sort(() => Math.random() - 0.5);
}
