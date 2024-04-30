import axios from "axios"
import { useState } from "react"

export const Catch:React.FC = () => {
    let userInput:number = 0

    const [pokemon, setPokemon] = useState<PokemonInterface>({
        name:"",
        image:""
    })

    const gatherInput = (input:any) => {
        userInput = input.target.value;
    }

    const getPokemon = async () => {
        const response = await axios.get("https://pokeapi.co/api/v2/pokemon/" + userInput);
    }

    return(
        <div className="home-page">
            <div className="home-container">
                <h3>yeah</h3>
                <input type="number" placeholder="enter search term" onChange={gatherInput}/>
                <button className="poke-button">go do the search</button>
            </div>
        </div>
    )
}
