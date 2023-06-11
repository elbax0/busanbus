import './App.css'
import {BrowserRouter} from "react-router-dom";
import RoutesSetup from "./components/RoutesSetup.tsx";

function App() {

    if (import.meta.env.MODE === 'production') {
        console.log = () => {};
        console.warn = () => {};
        console.error = () => {};
    }

    return (
        <>
            <BrowserRouter>
                <RoutesSetup/>
            </BrowserRouter>
        </>
    );
}

export default App;