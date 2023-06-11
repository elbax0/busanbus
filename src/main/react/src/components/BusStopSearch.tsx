import {ChangeEvent, FormEvent, useMemo, useState} from "react";
import {Box, Button, Input, Typography} from "@mui/material";
import BusStopList from "./BusStopList.tsx";

type BusStopListDtoType = {
    busStopList: Array<any>;
    totalPage: number;
    currPage: number;
    searchKeyword: string;
    pagingHandler: Function;
}

const BusStopSearch = () => {
    const [searchKeyword, setSearchKeyword] = useState('');
    const [busStopListDto, setBusStopListDto] = useState<BusStopListDtoType>({
        busStopList: [], currPage: 0, pagingHandler() {
        }, searchKeyword: "", totalPage: 0
    });

    const onChangeHandler = (e: ChangeEvent<HTMLInputElement>) => {
        e.preventDefault();
        setSearchKeyword(e.target.value);
    }

    const search = (keyword: string, page: number) => {
        // fetch(`http://localhost:8080/api/busStop/${keyword}?page=${page - 1}`)
        fetch(`https://bbus.cc/api/busStop/${keyword}?page=${page - 1}`)
            .then(response => {
                if (!response.ok) {
                    throw new Error();
                }
                return response.json();
            })
            .then(result => {
                console.log(result);
                setBusStopListDto({
                    ...result,
                    pagingHandler: search,
                });
            })
            .catch(err => {
                console.log(err);
            });
    }

    const busStopList = useMemo(() => {
        return <BusStopList {...busStopListDto}/>;
    }, [busStopListDto]);

    const onSubmit = (e : FormEvent<HTMLFormElement>) => {
        e.preventDefault();
        if (searchKeyword.trim() !== '') {
            search(searchKeyword, 1);
        }
    };

    return (
        <>
            <Box sx={{ mb: 1 }}>
                <form onSubmit={onSubmit} >
                    <Typography variant="h5">부산 버스 도착 정보 서비스</Typography>
                    <Box sx={{ marginTop: 3 }}>
                        <Input id="busStopName" onChange={onChangeHandler} placeholder="버스 정류장 이름" />
                        <Button sx={{ mx: 1 }}  type="submit" variant="contained" >검색</Button>
                    </Box>
                </form>
            </Box>
            {busStopList}
        </>
    );
};

export default BusStopSearch;