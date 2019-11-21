import React, {PureComponent} from 'react';
import {makeStyles} from '@material-ui/core/styles';
import List from "@material-ui/core/List";
import ListItem from "@material-ui/core/ListItem";
import ListItemIcon from "@material-ui/core/ListItemIcon";
import ListItemText from "@material-ui/core/ListItemText";

const useStyles = makeStyles(theme => ({
  root: {
    width: '100%',
    maxWidth: 360,
    backgroundColor: theme.palette.background.paper,
  },
}));

export default class ListComponent extends PureComponent {
  render() {
    return (
      <React.Fragment>
        <List component="nav" aria-label="contacts">
          <ListItem button>
            <ListItemIcon>
            </ListItemIcon>
            <ListItemText primary="Chelsea Otakan"/>
          </ListItem>
          <ListItem button>
            <ListItemText inset primary="Eric Hoffman"/>
          </ListItem>
        </List>
      </React.Fragment>
    );
  }

}
