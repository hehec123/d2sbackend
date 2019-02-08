import React, { Component } from 'react';
import PropTypes from 'prop-types';

import StorageGrid from './storageGrid';
import Equipment from './equipment';

const EQUIP = 0, INV = 1, TYPES = 5;

// Manages character storage, including the current storage type and configuring the storage grid.
export default class Storage extends Component {
  constructor(props) {
    super(props);

    this.state = {
      currType: INV,
    };

    this.onStorageChange = this.onStorageChange.bind(this);
  }

  static getStorageType(type) {
    return ['Equipment', 'Inventory', 'Stash', 'Belt', 'Horadric Cube'][type];
  }

  getItemsFrom(type) {
    const { items } = this.props;

    return items[type];
  }

  getMapFrom(type) {
    const { itemMaps } = this.props;

    return itemMaps[type];
  }

  onStorageChange(event) {
    this.setState({ currType: Number.parseInt(event.target.value, 10) });
  }

  render() {
    const { currType } = this.state;

    return (
      <div className="entry">
        <select value={currType} onChange={this.onStorageChange} className="form-control storageHeader">
          {
            [...Array(TYPES)].map((obj, i) => (
              <option key={i} value={i}>{Storage.getStorageType(i)}</option>
            ))
          }
        </select>

        {
          currType === EQUIP
          && (
            <Equipment items={this.equipment} />
          )
        }

        <StorageGrid
          type={currType}
          items={this.getItemsFrom(currType)}
          itemMap={this.getMapFrom(currType)}
          clickHandler={this.props.clickHandler}
          delHandler={this.props.delHandler}
        />
      </div>
    );
  }
}

Storage.propTypes = {
  clickHandler: PropTypes.func.isRequired,
  delHandler: PropTypes.func.isRequired,
  items: PropTypes.arrayOf(PropTypes.object).isRequired,
};
