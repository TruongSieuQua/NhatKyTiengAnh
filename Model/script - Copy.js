"use strict"
const { useState } = React;
const WordFamilyLink = "https://dictionary.cambridge.org/vi/dictionary/english-vietnamese/";
const WordSynonymLink = "https://www.vocabulary.com/dictionary/";

function FamilyItem(props) {
    let word = props.value;
    return <a href={WordFamilyLink + word} target="frame2" className="f-item">{" " + word + " "}</a>;
}

//Xử lý 1 từ loại noun || adj || verb || adverb gồm một mảng các words
function FamilyType(props) {
    const { id, type, words } = props;
    const listWordSameType = words.map(word => {
        return < FamilyItem key={id + type + word} value={word} />
    })
    return <>
        <span>{"(" + type + ")"}</span>
        {listWordSameType}
    </>
}
//Duyệt qua tất cả loại từ noun, adj, verb, adverb
const GetFamily = (props) => {
    const { id, family } = props;
    const famsComponent = Object.keys(family).map(type => {
        if (family[type].length > 0) {
            return < FamilyType key={id + type} id={id} type={type} words={family[type]} />
        }
    })
    return (<div className="family">{famsComponent}</div>)
};

function SynonymItem(props) {
    return (
        <a href={WordSynonymLink + props.value}
            target="frame3" className="s-item">
            {" " + props.value + " "}
        </a>
    )
}

const GetSynonyms = (props) => {
    const { id, synonyms } = props;
    let url = "https://www.vocabulary.com/dictionary/";
    if (!synonyms || synonyms.length == 0) return <></>;
    let syns_Component = synonyms.map(synonym => {
        return < SynonymItem key={id + synonym} value={synonym} />
    })
    return <div className="synonyms">Synonyms: {syns_Component}</div>
}


//Hàm thay thế từ trong câu thành answer
const ReplaceJSX = (props) => {
    const { str, oldValue, newValue } = props;
    let arr = str.split(oldValue, 2);
    let result = [arr[0]];
    for (let i = 1; i < arr.length; i++) {
        result.push(newValue);
        result.push(arr[i]);
    }
    return <>{result}</>;
}

//Một khối nghĩa tiếng Anh, nghĩa tiếng Việt và các examples
function MeaningItem(props) {
    const { id, word, mIndex, en, vi, examples } = props;
    //Xử lý khối các examples trong 1 khối nghĩa
    let examplesComponent = <></>;
    if(examples){
        examplesComponent = examples.map((example, eIndex) => {
            let exampleId = `${id}-m${mIndex}-e${eIndex}`;
            //<span className="answer">{word}</span>
            return (
                <li className="example" key={exampleId}>
                    < ReplaceJSX key={exampleId} str={example.en} oldValue={" " + word}
                        newValue={<> <span className="answer-warp"><span className="answer">{word}</span></span></>}
                    />
                </li>
            );
        });
    }
    return (<>
        <div className="meaning">
            <div className="en">{en}</div>
            <div className="vi">{vi}</div>
            <ul className="examples">{examplesComponent}</ul>
        </div>
    </>)
}

const GetMeanings = (props) => {
    const { id, word, meanings } = props;
    let meanings_Coponent = <></>;
    if(meanings){
        meanings_Coponent = meanings.map((meaning, mIndex) => {
            const { en, vi, examples } = meaning;
            let meaningId = `${id}-m${mIndex}`;
            return < MeaningItem key={meaningId}
                id={id} word={word} mIndex={mIndex}
                en={en?en:''} vi={vi?vi:''} examples={examples}
            />
        });
    }
    return (<div className="list-meanings">{meanings_Coponent}</div>)
}

// function CardItem(props) {
//     const [toggledClass, setToggledClass] = useState(() => {
//         return ' disabled';
//     });
//     function handleClick(e) {
//         //preState sẽ được truyền giá trị của toggledClass mỗi khi re-render
//         setToggledClass((preState) => {
//             return preState === ' disabled' ? '' : ' disabled';
//         });

//     }
//     const { word, ipa, family, synonyms, meanings } = props.wordInfo;
//     const id = props.id;
//     return (
//         <div className={`card${toggledClass}`} >
//             <div className="word">{word}</div>
//             <div className="ipa">{ipa}</div>
//             < GetFamily family={family} id={id} />
//             < GetMeanings meanings={meanings} word={word} id={id} />
//             < GetSynonyms synonyms={synonyms} id={id} />
//             <div className="text-answer"><label htmlFor={id}>Dap An: </label><input type="text" id={id} /></div>
//             <button className="btn btn-answer" type="button"
//                 onClick={handleClick}>Answer</button>
//             <button className="btn btn-reset" type="button"
//                 onClick={handleClick}>Reset</button>
//         </div>
//     )
// }

function CardItem(props) {
    const { word, ipa, family, synonyms, meanings } = props.wordInfo;
    const id = props.id;
    let unanswered = meanings.length > 0 ?'unanswered' : '';
    return (
        <div className={`card ${unanswered}`} >
            <div className="word">{word}</div>
            <div className="ipa">{ipa}</div>
            < GetFamily family={family} id={id} />
            < GetMeanings meanings={meanings} word={word} id={id} />
            < GetSynonyms synonyms={synonyms} id={id} />
            <div className="text-answer"><label htmlFor={id}>Dap An: </label><input type="text" id={id} /></div>
            <div className="card-btns">
                <button className="btn btn-answer" type="button">Answer</button>
                <button className="btn btn-reset" type="button">Reset</button>
            </div>
        </div>
    )
}

const RenderData = (props) => {
    const data = props.jsonData.data;
    return (
        <div className="word-cards">
            {data.map(wordInfo => {
                let id = wordInfo.word;
                return < CardItem wordInfo={wordInfo} key={id} id={id} />
            })}
        </div>
    )
}

function isValidCard(card){
    let meanings = card.getElementsByClassName("meaning");
    if(meanings && meanings.length > 0){
        return true;
    }
    return false;
}

function shuffle(jsonData) {
    jsonData.data.sort(() => Math.random() - 0.5);
    ReactDOM.render(<RenderData jsonData={jsonData} />, document.getElementById("root"));
}
/*
 * Shuffle + Reset
*/
function AddShuffleEvent(btn, type, jsonData, cards) {
    btn.addEventListener(type, () => {
        shuffle(jsonData);
        resetAll(cards);
    });
}
function resetAll(cards) {
    for (let card of cards) {
        notAnsweredCSS(card);
    }
}
/*
* Chế độ Review
*/
function AddchangeToReviewModeEvent(btn, type, cards) {
    btn.addEventListener(type, () => {
        resetAll(cards);
        for (let card of cards) {
            changeToReviewMode(card);
        }
    });
}

function changeToReviewMode(card) {
    let meanings = card.getElementsByClassName("meaning");
    if(isValidCard(card)){
        for (let meaning of meanings) {
            meaning.style.display = 'table';
        }
    }
}

/*
* Chế độ Test
*/
//AddChangeToTestModeEvent
function addChangeToTestModeEvent(btn, type, cards) {
    btn.addEventListener(type, () => {
        resetAll(cards);
        for (let card of cards) {
            changeToTestMode(card);
        }
    });
}

function changeToTestMode(card) {
    let meanings = card.getElementsByClassName("meaning");
    if(isValidCard(card)){
        for (let meaning of meanings) {
            meaning.style.display = 'none';
        };
        let i = Math.floor(Math.random() * (meanings.length));
        if (i >= meanings.length) { i = i - 1; }
        meanings[i].style.display = 'table';
    }
}

/*
* Hàm kiểm tra answer
*/
function correctAnswerCSS(card) {
    let answerInput = card.querySelector(".text-answer input");
    answerInput.classList.remove('incorrect');
    answerInput.classList.add('correct');

}

function incorrectAnswerCSS(card) {
    let answerInput = card.querySelector(".text-answer input");
    answerInput.classList.remove('correct');
    answerInput.classList.add('incorrect');
}

function notAnsweredCSS(card) {
    card.classList.add("unanswered");
    let answerInput = card.querySelector(".text-answer input");
    answerInput.classList.remove('correct');
    answerInput.classList.remove('incorrect');
    answerInput.value = '';
}

function checkAnswer(card) {
    let answerWord = card.querySelector(".word").innerText;
    let answerInput = card.querySelector(".text-answer input").value;
    card.classList.remove("unanswered");
    if (answerWord === answerInput) {
        correctAnswerCSS(card);
    } else {
        incorrectAnswerCSS(card);
    }
}

function addCheckAnswerEvent(btns, type) {
    for (let btn of btns) {
        let card = getCardOfTaget(btn);
        btn.addEventListener(type, () => {
            checkAnswer(card);
        });
    }
}

function addResetAnswerEvent(btns, type) {
    for (let btn of btns) {
        let card = getCardOfTaget(btn);
        btn.addEventListener(type, () => {
            notAnsweredCSS(card);
        })
    }
}

function getCardOfTaget(element) {
    let card = element;
    while (!card.classList.contains("card")) {
        card = card.parentElement;
    }
    return card;
}