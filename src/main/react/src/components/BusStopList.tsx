import {Table, TableBody, TableCell, TableContainer, TableHead, TableRow, Typography} from "@mui/material";
import PagingComponent from "./PagingComponent.tsx";
import BusStopLocationModal from "./BusStopLocationModal.tsx";
import BusArrivalModal from "./BusArrivalModal.tsx";

type BusStopType = {
    bstopid: number;
    bstopnm: string;
    arsno: number;
    gpsx: string;
    gpsy: string
    stoptype: string;
};

type BusStopListType = {
    busStopList: Array<BusStopType>;
    totalPage: number;
    currPage: number;
    searchKeyword: string;
    pagingHandler: Function;
}

const BusStopList = (props: BusStopListType) => {
    if (props.searchKeyword === '') {
        return null;
    }
    console.log(props);
    const busStopList = props.busStopList;
    const pagingData = {
        totalPage: props.totalPage,
        currPage: props.currPage + 1,
        searchKeyword: props.searchKeyword,
        pagingHandler: props.pagingHandler
    };


    return (
        <>
            <TableContainer>
                <Table sx={{ minWidth: 650 }} aria-label="simple table">
                    <TableHead>
                        <TableRow>
                            <TableCell align="center">정류장 이름</TableCell>
                            <TableCell align="center">정류장 ID</TableCell>
                            <TableCell align="center">타입</TableCell>
                            <TableCell align="center">위치</TableCell>
                            <TableCell align="center">도착정보 확인</TableCell>
                        </TableRow>
                    </TableHead>
                    <TableBody>
                        {busStopList.length == 0 ?
                            <TableRow>
                                <TableCell colSpan={5}>
                                    <Typography align="center">검색 결과가 없습니다.</Typography>
                                </TableCell>
                            </TableRow> :
                            busStopList.map((busStop) =>
                                <TableRow key={busStop.bstopid}>
                                    <TableCell align="center">{busStop.bstopnm}</TableCell>
                                    <TableCell align="center">{busStop.bstopid}</TableCell>
                                    <TableCell align="center">{busStop.stoptype}</TableCell>
                                    <TableCell align="center">
                                        <BusStopLocationModal busStopName={busStop.bstopnm} busStopId={busStop.bstopid}/>
                                    </TableCell>
                                    <TableCell align="center">
                                        <BusArrivalModal busStopName={busStop.bstopnm} busStopId={busStop.bstopid}/>
                                    </TableCell>
                                </TableRow>
                            )
                        }
                    </TableBody>
                </Table>
            </TableContainer>
            <PagingComponent {...pagingData}/>
        </>
    );
};

export default BusStopList;