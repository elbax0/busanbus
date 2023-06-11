import {Box, Pagination} from "@mui/material";
import {ChangeEvent} from "react";

type PagingComponentType = {
    totalPage: number;
    currPage: number;
    searchKeyword: string;
    pagingHandler: Function;
};

const PagingComponent = (props: PagingComponentType) => {
    if (!props.totalPage || props.totalPage === 0) {
        return null;
    }

    const searchKeyword = props.searchKeyword;
    const onPageChange = (e: ChangeEvent<unknown>, page: number) => {
        e.preventDefault();
        props.pagingHandler(searchKeyword, page);
    }

    return (
        <Box
            display="flex"
            justifyContent="center"
            alignItems="center"
            style={{ outline: "none"}}
            sx={{ boxShadow: 0 }}
        >
            <Pagination
                    sx={{mt: 3}}
                    count={props.totalPage}
                    page={props.currPage}
                    showFirstButton
                    showLastButton
                    onChange={onPageChange}
                    style={{ outline: "none"}}
            />
        </Box>
    );
};

export default PagingComponent;