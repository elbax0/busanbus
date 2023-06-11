import {Routes, Route} from "react-router-dom";
import NotFound from "./NotFound.tsx";
import BusStopSearch from "./BusStopSearch.tsx";

const RoutesSetup = () => {
    return (
        <Routes>
            <Route path="/" element={<BusStopSearch/>}/>
            <Route path="*" element={<NotFound/>} />
        </Routes>
    );
};

export default RoutesSetup;